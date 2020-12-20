<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="驾驶员">
              <a-input placeholder="请输入驾驶员" v-model="queryParam.driverName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="手机号">
              <a-input placeholder="请输入手机号" v-model="queryParam.phoneNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('驾驶员管理')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <driver-modal ref="modalForm" @ok="modalFormOk"></driver-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import DriverModal from './modules/DriverModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'DriverList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      DriverModal,
      JSuperQuery,
    },
    data () {
      return {
        description: '驾驶员管理管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'运输单位',
            align:"center",
            dataIndex: 'factory_dictText'
          },
          {
            title:'准驾车型',
            align:"center",
            dataIndex: 'driverType_dictText'
          },
          {
            title:'驾驶员',
            align:"center",
            dataIndex: 'driverName'
          },
          {
            title:'身份证号',
            align:"center",
            dataIndex: 'cardNo'
          },
          {
            title:'手机号',
            align:"center",
            dataIndex: 'phoneNo'
          },
          {
            title:'联系地址',
            align:"center",
            dataIndex: 'contactAddress'
          },
          {
            title:'驾驶证号',
            align:"center",
            dataIndex: 'driverNo'
          },
          {
            title:'驾驶证有效期',
            align:"center",
            dataIndex: 'driverPeriodTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'驾驶员从业资格证号',
            align:"center",
            dataIndex: 'driverLicenceNo'
          },
          {
            title:'从业资格证有效期',
            align:"center",
            dataIndex: 'driverLicenceTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/basic/driver/list",
          delete: "/basic/driver/delete",
          deleteBatch: "/basic/driver/deleteBatch",
          exportXlsUrl: "/basic/driver/exportXls",
          importExcelUrl: "basic/driver/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'sel_search',value:'factory',text:'运输单位',dictTable:'lms_transportation_company', dictText:'name', dictCode:'code'})
        fieldList.push({type:'string',value:'driverType',text:'准驾车型',dictCode:'BASIC_DRIVER_TYPE'})
        fieldList.push({type:'string',value:'driverName',text:'驾驶员',dictCode:''})
        fieldList.push({type:'string',value:'cardNo',text:'身份证号',dictCode:''})
        fieldList.push({type:'string',value:'phoneNo',text:'手机号',dictCode:''})
        fieldList.push({type:'string',value:'contactAddress',text:'联系地址',dictCode:''})
        fieldList.push({type:'string',value:'driverNo',text:'驾驶证号',dictCode:''})
        fieldList.push({type:'date',value:'driverPeriodTime',text:'驾驶证有效期'})
        fieldList.push({type:'string',value:'driverLicenceNo',text:'驾驶员从业资格证号',dictCode:''})
        fieldList.push({type:'date',value:'driverLicenceTime',text:'从业资格证有效期'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
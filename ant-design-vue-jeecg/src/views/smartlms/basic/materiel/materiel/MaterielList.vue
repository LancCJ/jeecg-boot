<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="物料名称">
              <a-input placeholder="请输入物料名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="物料编码">
              <a-input placeholder="请输入物料编码" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="业务类型">
                <j-dict-select-tag placeholder="请选择业务类型" v-model="queryParam.bizType" dictCode="BASIC_MATERIEL_BIZ_TYPE"/>
              </a-form-item>
            </a-col>
          </template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('物料信息')">导出</a-button>
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

    <materiel-modal ref="modalForm" @ok="modalFormOk"></materiel-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MaterielModal from './modules/MaterielModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'MaterielList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JDictSelectTag,
      MaterielModal,
      JSuperQuery,
    },
    data () {
      return {
        description: '物料信息管理页面',
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
            title:'物料名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'物料编码',
            align:"center",
            dataIndex: 'code'
          },
          {
            title:'业务类型',
            align:"center",
            dataIndex: 'bizType_dictText'
          },
          {
            title:'包装类型',
            align:"center",
            dataIndex: 'packageType_dictText'
          },
          {
            title:'物料分组',
            align:"center",
            dataIndex: 'materielGroup_dictText'
          },
          {
            title:'物料标识',
            align:"center",
            dataIndex: 'identification'
          },
          {
            title:'物料规格',
            align:"center",
            dataIndex: 'specification'
          },
          {
            title:'物料描述',
            align:"center",
            dataIndex: 'description'
          },
          {
            title:'需要采样',
            align:"center",
            dataIndex: 'izSampling',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['izSampling'], text) : ''),
          },
          {
            title:'需要确认',
            align:"center",
            dataIndex: 'izConfirm',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['izConfirm'], text) : ''),
          },
          {
            title:'需要扣杂',
            align:"center",
            dataIndex: 'izDeduction',
            customRender: (text) => (text ? filterMultiDictText(this.dictOptions['izDeduction'], text) : ''),
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
          list: "/basic/materiel/list",
          delete: "/basic/materiel/delete",
          deleteBatch: "/basic/materiel/deleteBatch",
          exportXlsUrl: "/basic/materiel/exportXls",
          importExcelUrl: "basic/materiel/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
      this.$set(this.dictOptions, 'izSampling', [{text:'是',value:'Y'},{text:'否',value:'N'}])
      this.$set(this.dictOptions, 'izConfirm', [{text:'是',value:'Y'},{text:'否',value:'N'}])
      this.$set(this.dictOptions, 'izDeduction', [{text:'是',value:'Y'},{text:'否',value:'N'}])
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
        fieldList.push({type:'string',value:'name',text:'物料名称',dictCode:''})
        fieldList.push({type:'string',value:'code',text:'物料编码',dictCode:''})
        fieldList.push({type:'string',value:'bizType',text:'业务类型',dictCode:'BASIC_MATERIEL_BIZ_TYPE'})
        fieldList.push({type:'string',value:'packageType',text:'包装类型',dictCode:'BASIC_MATERIEL_PKG_TYPE'})
        fieldList.push({type:'string',value:'materielGroup',text:'物料分组',dictCode:'lms_materiel_group,name,code'})
        fieldList.push({type:'string',value:'identification',text:'物料标识',dictCode:''})
        fieldList.push({type:'string',value:'specification',text:'物料规格',dictCode:''})
        fieldList.push({type:'string',value:'description',text:'物料描述',dictCode:''})
        fieldList.push({type:'switch',value:'izSampling',text:'需要采样'})
        fieldList.push({type:'switch',value:'izConfirm',text:'需要确认'})
        fieldList.push({type:'switch',value:'izDeduction',text:'需要扣杂'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="物料名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入物料名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物料编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入物料编码"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="业务类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['bizType', validatorRules.bizType]" :trigger-change="true" dictCode="BASIC_MATERIEL_BIZ_TYPE" placeholder="请选择业务类型" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="包装类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['packageType', validatorRules.packageType]" :trigger-change="true" dictCode="BASIC_MATERIEL_PKG_TYPE" placeholder="请选择包装类型" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物料分组" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['materielGroup']" :trigger-change="true" dictCode="lms_materiel_group,name,code" placeholder="请选择物料分组" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物料标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['identification']" placeholder="请输入物料标识"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物料规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['specification']" placeholder="请输入物料规格"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="物料描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['description']" placeholder="请输入物料描述"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="需要采样" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch v-decorator="['izSampling', validatorRules.izSampling]"  ></j-switch>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="需要确认" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch v-decorator="['izConfirm', validatorRules.izConfirm]"  ></j-switch>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="需要扣杂" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch v-decorator="['izDeduction', validatorRules.izDeduction]"  ></j-switch>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSwitch from '@/components/jeecg/JSwitch'

  export default {
    name: 'MaterielForm',
    components: {
      JFormContainer,
      JDictSelectTag,
      JSwitch,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          name: {
            rules: [
              { required: true, message: '请输入物料名称!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_materiel', 'name', value, this.model.id, callback)},
            ]
          },
          code: {
            rules: [
              { required: true, message: '请输入物料编码!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_materiel', 'code', value, this.model.id, callback)},
            ]
          },
          bizType: {
            rules: [
              { required: true, message: '请输入业务类型!'},
            ]
          },
          packageType: {
            rules: [
              { required: true, message: '请输入包装类型!'},
            ]
          },
          izSampling: {
            rules: [
              { required: true, message: '请输入需要采样!'},
            ]
          },
          izConfirm: {
            rules: [
              { required: true, message: '请输入需要确认!'},
            ]
          },
          izDeduction: {
            rules: [
              { required: true, message: '请输入需要扣杂!'},
            ]
          },
        },
        url: {
          add: "/basic/materiel/add",
          edit: "/basic/materiel/edit",
          queryById: "/basic/materiel/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','code','bizType','packageType','materielGroup','identification','specification','description','izSampling','izConfirm','izDeduction'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'name','code','bizType','packageType','materielGroup','identification','specification','description','izSampling','izConfirm','izDeduction'))
      },
    }
  }
</script>
<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="物流企业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-search-select-tag v-decorator="['logistics']" dict="lms_transportation_company,name,code"  />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="押运员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['supercargor', validatorRules.supercargor]" placeholder="请输入押运员"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['cardNo', validatorRules.cardNo]" placeholder="请输入身份证号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="押运证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['supercargoNo', validatorRules.supercargoNo]" placeholder="请输入押运证号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['phoneNo', validatorRules.phoneNo]" placeholder="请输入手机号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="联系地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contactAddress']" placeholder="请输入联系地址"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contactEmail', validatorRules.contactEmail]" placeholder="请输入邮箱"  ></a-input>
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
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'

  export default {
    name: 'SupercargoForm',
    components: {
      JFormContainer,
      JSearchSelectTag,
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
          supercargor: {
            rules: [
              { required: true, message: '请输入押运员!'},
            ]
          },
          cardNo: {
            rules: [
              { required: true, message: '请输入身份证号!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_supercargo', 'card_no', value, this.model.id, callback)},
            ]
          },
          supercargoNo: {
            rules: [
              { required: true, message: '请输入押运证号!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_supercargo', 'supercargo_no', value, this.model.id, callback)},
            ]
          },
          phoneNo: {
            rules: [
              { required: true, message: '请输入手机号!'},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          contactEmail: {
            rules: [
              { required: false},
              { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},
            ]
          },
        },
        url: {
          add: "/basic/supercargo/add",
          edit: "/basic/supercargo/edit",
          queryById: "/basic/supercargo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'logistics','supercargor','cardNo','supercargoNo','phoneNo','contactAddress','contactEmail'))
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
        this.form.setFieldsValue(pick(row,'logistics','supercargor','cardNo','supercargoNo','phoneNo','contactAddress','contactEmail'))
      },
    }
  }
</script>
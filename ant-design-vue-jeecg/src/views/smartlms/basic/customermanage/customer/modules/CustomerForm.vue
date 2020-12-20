<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="客户编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入客户编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入客户名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['grade']" :trigger-change="true" dictCode="BASIC_CUSTOMER_GRADE" placeholder="请选择等级" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="客户联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contact']" placeholder="请输入客户联系人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['salesman']" placeholder="请输入业务员"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所属区域1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['parentArea1']" placeholder="请输入所属区域1"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所属区域2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['parentArea2']" placeholder="请输入所属区域2"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所属区域3" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['parentArea3']" placeholder="请输入所属区域3"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所属区域4" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['parentArea4']" placeholder="请输入所属区域4"  ></a-input>
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

  export default {
    name: 'CustomerForm',
    components: {
      JFormContainer,
      JDictSelectTag,
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
          code: {
            rules: [
              { required: true, message: '请输入客户编号!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_customer', 'code', value, this.model.id, callback)},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入客户名称!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_customer', 'name', value, this.model.id, callback)},
            ]
          },
        },
        url: {
          add: "/basic/customer/add",
          edit: "/basic/customer/edit",
          queryById: "/basic/customer/queryById"
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
          this.form.setFieldsValue(pick(this.model,'code','name','grade','contact','salesman','parentArea1','parentArea2','parentArea3','parentArea4'))
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
        this.form.setFieldsValue(pick(row,'code','name','grade','contact','salesman','parentArea1','parentArea2','parentArea3','parentArea4'))
      },
    }
  }
</script>
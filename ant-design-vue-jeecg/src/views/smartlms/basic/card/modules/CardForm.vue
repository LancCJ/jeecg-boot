<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="卡类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['type', validatorRules.type]" :trigger-change="true" dictCode="BASIC_CARD_TYPE" placeholder="请选择卡类型" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="印刷号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['printingNo', validatorRules.printingNo]" placeholder="请输入印刷号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="ID卡" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['idNo', validatorRules.idNo]" placeholder="请输入ID卡"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="IC卡" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['icNo', validatorRules.icNo]" placeholder="请输入IC卡"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-switch v-decorator="['izEnable']"  ></j-switch>
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
    name: 'CardForm',
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
          type: {
            rules: [
              { required: true, message: '请输入卡类型!'},
            ]
          },
          printingNo: {
            rules: [
              { required: false},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_card', 'printing_no', value, this.model.id, callback)},
            ]
          },
          idNo: {
            rules: [
              { required: false},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_card', 'id_no', value, this.model.id, callback)},
            ]
          },
          icNo: {
            rules: [
              { required: false},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_card', 'ic_no', value, this.model.id, callback)},
            ]
          },
        },
        url: {
          add: "/basic/card/add",
          edit: "/basic/card/edit",
          queryById: "/basic/card/queryById"
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
          this.form.setFieldsValue(pick(this.model,'type','printingNo','idNo','icNo','izEnable'))
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
        this.form.setFieldsValue(pick(row,'type','printingNo','idNo','icNo','izEnable'))
      },
    }
  }
</script>
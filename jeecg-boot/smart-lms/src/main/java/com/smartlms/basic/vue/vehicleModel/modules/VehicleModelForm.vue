<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="车型编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['code', validatorRules.code]" placeholder="请输入车型编号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车型名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入车型名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="荷载重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['loadWeight', validatorRules.loadWeight]" placeholder="请输入荷载重量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="超限重量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['overWeight', validatorRules.overWeight]" placeholder="请输入超限重量" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="轴型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['axialType', validatorRules.axialType]" placeholder="请输入轴型"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="轴数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['axialNum', validatorRules.axialNum]" placeholder="请输入轴数" style="width: 100%" />
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

  export default {
    name: 'VehicleModelForm',
    components: {
      JFormContainer,
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
              { required: true, message: '请输入车型编号!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_vehicle_model', 'code', value, this.model.id, callback)},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入车型名称!'},
              { validator: (rule, value, callback) => validateDuplicateValue('lms_vehicle_model', 'name', value, this.model.id, callback)},
            ]
          },
          loadWeight: {
            rules: [
              { required: true, message: '请输入荷载重量!'},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
            ]
          },
          overWeight: {
            rules: [
              { required: false},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
            ]
          },
          axialType: {
            rules: [
              { required: true, message: '请输入轴型!'},
            ]
          },
          axialNum: {
            rules: [
              { required: true, message: '请输入轴数!'},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
            ]
          },
        },
        url: {
          add: "/basic/vehicleModel/add",
          edit: "/basic/vehicleModel/edit",
          queryById: "/basic/vehicleModel/queryById"
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
          this.form.setFieldsValue(pick(this.model,'code','name','loadWeight','overWeight','axialType','axialNum'))
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
        this.form.setFieldsValue(pick(row,'code','name','loadWeight','overWeight','axialType','axialNum'))
      },
    }
  }
</script>
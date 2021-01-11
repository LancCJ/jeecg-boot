<template>
    <k-form-design
      ref='flowableDesignForm'
      @save="handleSave"
    />
</template>

<script>
  import '@/components/extension/k-form-design/styles/form-design.less'
  import {getDesignForm,editDesignForm} from '../../../api/api'
  import jsonFormat from '../../../components/extension/k-form-design/packages/KFormDesign/config/jsonFormat'

  var that;

  export default {
    name: 'formDesign',

    data:{
      id: '',
      form_key: '',
      form_name: '',
      form_version: '',
      form_json:'',

      jsonData: {
        list: [
          {
            type: 'textarea',
            label: '文本框',
            icon: 'icon-edit',
            options: {
              width: '100%',
              minRows: 4,
              maxRows: 6,
              defaultValue: '',
              disabled: false,
              placeholder: '请输入'
            },
            model: 'textarea_1584772782841',
            key: 'textarea_1584772782841',
            rules: [
              {
                required: false,
                message: '必填项'
              }
            ]
          }
        ],
        config: {
          layout: 'horizontal',
          labelCol: {
            span: 4
          },
          wrapperCol: {
            span: 18
          },
          hideRequiredMark: false,
          customStyle: ''
        }
      }

    },

    mounted () {
      that = this;
    },

    watch: {

      $route: {
        immediate: true,
        handler() {
          console.log('============= online href  $route props 用来接收传入的参数信息============= ');
          this.id = this.$route.query.id;
          if(this.id!=null){ //说明是传值跳转的 否则不执行下面赋值代码
            this.getFormJsonById(this.id);
          }
        }
      }
    },

    methods: {
      async getFormJsonById(id){
        getDesignForm({
          id:id
        }).then((res) => {
          var msg = res.message;
          if (res.success) {
            that.form_key = res.result.formKey;
            that.form_name = res.result.formName;
            that.form_version = res.result.formVersion;

            that.buildFlowableDesignForm(res.result.formJson);
          }else{
            alert(msg);
          }
        });
      },
      async buildFlowableDesignForm (formJson) {
        if(formJson.length!=0){
          var _formJson = JSON.parse(formJson);
          that.$refs.flowableDesignForm.handleSetData(_formJson);
        }
      },
      handleSave(values) {
        that.form_json = values;
        editDesignForm({
          id: that.id,
          formJson: that.form_json
        }).then((res) => {
          var msg = res.message;
          if (res.success) {
            alert('表单数据提交成功!');
          }else{
            alert(msg);
          }
        });
      }
    }
  }
</script>

<style scoped type="less">



</style>
<#-- 自定义的分页指令 (powered by qiujy)
    属性：
   pageNo      当前页号(int类型)
   pageSize    每页要显示的记录数(int类型)
   toURL       点击分页标签时要跳转到的目标URL(string类型)
   recordCount 总记录数(int类型)
 -->
<#macro pager pageNo pageSize toURL recordCount> 
  <#-- 定义局部变量pageCount保存总页数 -->
  <#assign pageCount=((recordCount + pageSize - 1) / pageSize)?int> 
 <#if recordCount==0><#return/></#if>
<#-- 输出分页样式 -->
<style type="text/css">
.pagination li.disabled {}
</style>
<div style="margin:0px auto; text-align:center" >
<#-- 页号越界处理 -->
  <#if (pageNo > pageCount)>
    <#assign pageNo=pageCount>
  </#if>
  <#if (pageNo < 1)>
    <#assign pageNo=1>
  </#if>
<#-- 输出分页表单 -->

<form method="POST" action="" name="qPagerForm">
<ul class="pagination">
<#-- 把请求中的所有参数当作隐藏表单域(无法解决一个参数对应多个值的情况) -->
<#list RequestParameters?keys as key>
<#if (key!="pageNo" && RequestParameters[key]??)>
<input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
</#if>
</#list>
<input type="hidden" name="_csrf" value="${_csrf.token}">
<input type="hidden" name="pageNo" value="${pageNo}"/>
<#-- 上一页处理 -->
  <#if (pageNo == 1)>
<li  class="disabled" ><a  aria-label="Previous"><i class="fa fa-angle-left"></i>上一页</a></li>
  <#else>
<li><a href="javascript:turnOverPage(${pageNo - 1})" aria-label="Previous"><i class="fa fa-angle-left"></i>上一页</a></li>
  </#if>
<#-- 如果前面页数过多,显示... -->
 <#assign start=1>
 <#if (pageNo > 4)>
    <#assign start=(pageNo - 1)>
<li><a href="javascript:turnOverPage(1)">1</a></li>
<li><a href="javascript:turnOverPage(2)">2</a>&hellip;</li>
 </#if>
<#-- 显示当前页号和它附近的页号 -->
 <#assign end=(pageNo + 1)>
 <#if (end > pageCount)>
  <#assign end=pageCount>
 </#if>
  <#list start..end as i>
    <#if (pageNo==i)>
<li><a style="background-color:#f3f1f1!important;">${i}</a></li>
  <#else>
<li><a href="javascript:turnOverPage(${i})">${i}</a>     </li>
    </#if>
  </#list>
<#-- 如果后面页数过多,显示... -->
  <#if (end < pageCount - 2)>
&hellip; 
  </#if>
  <#if (end < pageCount - 1)>
<li><a href="javascript:turnOverPage(${pageCount - 1})">${pageCount-1}</a></li>
  </#if>
  <#if (end < pageCount)>
<li><a href="javascript:turnOverPage(${pageCount})">${pageCount}</a></li>
  </#if>
<#-- 下一页处理 -->
  <#if (pageNo == pageCount)>
<li class="disabled" > <a aria-label="Next" >下一页<i class="fa fa-angle-right"></i></a></li>
  <#else>
<li><a href="javascript:turnOverPage(${pageNo + 1})" aria-label="Next">下一页<i class="fa fa-angle-right"></i></a></li>
  </#if>
  </ul>
</form>
<script language="javascript">
  function turnOverPage(no){
    var qForm=document.qPagerForm;
    if(no>${pageCount}){no=${pageCount};}
    if(no<1){no=1;}
    qForm.pageNo.value=no;
    qForm.action="${toURL}";
    qForm.submit();
  }
</script>

</div>
</#macro> 
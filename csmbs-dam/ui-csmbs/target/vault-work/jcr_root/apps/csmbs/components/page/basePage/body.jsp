<%--
  ==============================================================================
  Body Page 
  ==============================================================================
--%>
<%@include file="/apps/csmbs/global/global.jsp"%>
<%@page session="false"%>
<%@page import="com.day.cq.wcm.api.WCMMode"%>
<c:set var="isEditMode" value="<%=(WCMMode.fromRequest(request) == WCMMode.EDIT)%>"/>
<body>

<%-- Content include --%>
<cq:include script="body_content.jsp"/>
<%-- End Content include --%>

<%-- Footer include --%>
<cq:include script="body_footer.jsp"/>
<%-- End Footer include --%>
</body>

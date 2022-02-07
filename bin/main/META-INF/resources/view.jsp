
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %> 

<portlet:defineObjects />
<liferay-ui:success key="success" message="Usuario Registrado Correctamente"/>

<liferay-ui:error key="error" message="Error al Registrar Usuario" />

<%
	String userFullName = (String) renderRequest.getAttribute("user");
%>

<portlet:actionURL var="registerUserActionURL" name="registerUser"></portlet:actionURL>

<p>
	<b><liferay-ui:message key="Hello"/><%=userFullName%></b>
</p>


<aui:form action="<%=registerUserActionURL%>" name="registerUser" method="POST">

		<aui:fieldset label="New User Account Registration">
			<aui:row>
				<aui:col width="50">
					<aui:input label="First Name" name="firstName" type="text" >
						
					</aui:input>
				</aui:col>
				<aui:col width="50">
					<aui:input label="Middle Name" name="middleName" type="text" />
				</aui:col>
				<aui:col width="50">
					<aui:input label="Last Name" name="lastName" type="text" >
						<aui:validator name="required" errorMessage="Field is required"></aui:validator>
					</aui:input>
				</aui:col>
				<aui:col width="50">
					<aui:input label="Screen Name" name="screenName" type="text" />
				</aui:col>
				<aui:col width="50">
					<aui:input label="Birthday" name="birthday" type="date" />
				</aui:col>
				<aui:col width="50">
					<aui:select label="Sex" name="male">
						<aui:option value="true">Male</aui:option>
						<aui:option value="false">Female</aui:option>
					</aui:select>
				</aui:col>
			</aui:row>
			<aui:row>
				<aui:col width="50">
					<aui:input label="Email" name="email" type="email" >
						<aui:validator name="required"></aui:validator>
						<aui:validator name="email"></aui:validator>
					</aui:input>
				</aui:col>
				<aui:col width="50">
					<aui:input label="Password" name="password1" type="password" >
						<aui:validator name="required"></aui:validator>
						<aui:validator name="minLength">6</aui:validator>
					</aui:input>
				</aui:col>
				<aui:col width="50">
					<aui:input label="Confirm Password" name="password2" type="password" >
						<aui:validator name="required"></aui:validator>
						<aui:validator name="minLength">6</aui:validator>
						<aui:validator name="equalTo">'#<portlet:namespace />password1'</aui:validator>
					</aui:input>
				</aui:col>
				<aui:col width="50">
					<aui:input label="Job Title" name="jobTitle" type="text" />
				</aui:col>
			</aui:row>
		</aui:fieldset>
	<aui:button-row>
		<aui:button name="submitButton" type="submit" value="Submit" />
	</aui:button-row>
</aui:form>
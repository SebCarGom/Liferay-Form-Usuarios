package show.user.form.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import show.user.form.constants.ShowUserFormPortletKeys;

/**
 * @author scarnero
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ShowUserForm",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ShowUserFormPortletKeys.SHOWUSERFORM,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ShowUserFormPortlet extends MVCPortlet {

	private static Log _log = LogFactoryUtil.getLog(ShowUserFormPortlet.class);

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		User user = (User) renderRequest.getAttribute(WebKeys.USER);

		renderRequest.setAttribute("user", user.getFullName());

		super.doView(renderRequest, renderResponse);

	}

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		try {
			if (registerUser(actionRequest, actionResponse)) {
				SessionMessages.add(actionRequest, "success");
			} else {
				SessionErrors.add(actionRequest, "error");
			}

		} catch (PortalException e) {
			_log.error("Error en registerUser. ", e);

			SessionErrors.add(actionRequest, "error");
		}

//		super.processAction(actionRequest, actionResponse);
	}

	public boolean registerUser(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, PortalException {

		boolean validation = false;

		String firstName = ParamUtil.getString(actionRequest, "firstName");

		if (firstName.isEmpty() || firstName == null) {
			return validation;
		}

		String lastName = ParamUtil.getString(actionRequest, "lastName");

		if (lastName.isEmpty() || lastName == null) {
			return validation;
		}

		String email = ParamUtil.getString(actionRequest, "email");

		if (email.isEmpty() || email == null) {
			return validation;
		}

		String password1 = ParamUtil.getString(actionRequest, "password1");

		String password2 = ParamUtil.getString(actionRequest, "password2");

		if (!(password1.equals(password2))) {
			return false;
		}

		String middleName = ParamUtil.getString(actionRequest, "middleName");

		String screenName = ParamUtil.getString(actionRequest, "screenName");

		Date birthday = ParamUtil.getDate(actionRequest, "birthday", DateFormat.getDateInstance());

		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");

		boolean male = ParamUtil.getBoolean(actionRequest, "male");

		long[] groupIds = null;
		long[] organizationIds = {};
		long[] roleIds = {};
		long[] userGroupIds = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(birthday);
		int birthdayMonth = cal.get(Calendar.MONTH);
		int birthdayDay = cal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = cal.get(Calendar.YEAR);

		ServiceContext serviceContext = new ServiceContext();
		Date date = new Date();
		serviceContext.setUuid(UUID.randomUUID().toString());
		serviceContext.setCreateDate(date);
		serviceContext.setModifiedDate(date);

		UserLocalServiceUtil.addUser(0L, PortalUtil.getDefaultCompanyId(), false, password1, password2, true,
				screenName, email, 0L, "", LocaleUtil.getDefault(), firstName, middleName, lastName, 0L, 1L, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
				false, serviceContext);

		validation = true;

		return validation;
	}
}
package com.example.template.ui.staff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.template.BR;
import com.example.template.R;
import com.example.template.common.Constants;
import com.example.template.data.bean.Staff;
import com.example.template.ui.base.BaseViewPagerFragment;
import com.example.template.ui.staff.vm.StaffFormState;
import com.example.template.util.StaffFormValidator;
import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class StaffFormActivity extends BaseActivity {

    private StaffFormState state;

    public static void actionStart(Context context) {
        actionStart(context, null);
    }

    public static void actionStart(Context context, Staff staff) {
        Intent intent = new Intent(context, StaffFormActivity.class);
        intent.putExtra(Constants.STAFF_KEY, staff);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentList();
    }

    private void initFragmentList() {
        List<BaseViewPagerFragment> fragmentList = new ArrayList<>();
        StaffBasicFormFragment basicFormFragment = new StaffBasicFormFragment();
        basicFormFragment.setTitle(R.string.basic_info);
        fragmentList.add(basicFormFragment);
        StaffAdditionalFormFragment additionalFormFragment = new StaffAdditionalFormFragment();
        additionalFormFragment.setTitle(R.string.additional_info);
        fragmentList.add(additionalFormFragment);
        state.fragmentList.set(fragmentList);
    }

    @Override
    protected void initViewModel() {
        state = getActivityScopeViewModel(StaffFormState.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        getParameters();
        return new DataBindingConfig(R.layout.activity_staff_form, BR.vm, state)
                .addBindingParam(BR.click, new StaffFormClickProxy());
    }

    private void getParameters() {
        Intent intent = getIntent();
        Staff staff = (Staff) intent.getSerializableExtra(Constants.STAFF_KEY);
        if (staff != null) {
            state.staff.set(staff);
            state.formStatus.set(Constants.FORM_STATUS_EDIT);
        }
    }

    public class StaffFormClickProxy {

        public void submit() {
            Toasty.info(StaffFormActivity.this, "姓名是：" + state.staff.get().getName(), Toast.LENGTH_SHORT, true).show();
            if (!StaffFormValidator.isValid(state)) {
//                XToastUtils.error(R.string.save_failed);
                return;
            }
//            XToastUtils.success(R.string.save_successfully);
        }

    }

}

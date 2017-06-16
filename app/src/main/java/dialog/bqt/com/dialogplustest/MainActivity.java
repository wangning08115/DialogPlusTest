package dialog.bqt.com.dialogplustest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends ListActivity {
	private boolean expanded = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"DialogPlus官方demo",
				"ViewHolder，BOTTOM",
				"ViewHolder，TOP",
				"ListHolder，ArrayAdapter，CENTER",
				"ListHolder，自定义Adapter，【CENTER_HORIZONTAL】",
				"GridHolder，自定义Adapter，CENTER_VERTICAL",};
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(array))));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		expanded = !expanded;
		switch (position) {
			case 0:
				startActivity(new Intent(this, DialogPlusActivity.class));
				break;
			case 1:
				showDialogPlus(new ViewHolder(R.layout.content), null, Gravity.BOTTOM, expanded);
				break;
			case 2:
				showDialogPlus(new ViewHolder(R.layout.content2), null, Gravity.TOP, expanded);
				break;
			case 3:
				String[] array = new String[]{"包青天", "白乾涛", "baiqiantao", "0909082401"};
				showDialogPlus(new ListHolder(), new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array)
						, Gravity.CENTER, expanded);
				break;
			case 4:
				showDialogPlus(new ListHolder(), new SimpleAdapter(this, false), Gravity.CENTER_HORIZONTAL, expanded);
				break;
			case 5:
				showDialogPlus(new GridHolder(3), new SimpleAdapter(this, true), Gravity.CENTER_VERTICAL, expanded);
				break;
		}
	}

	private void showDialogPlus(Holder holder, BaseAdapter adapter, int gravity, boolean expanded) {
		DialogPlusBuilder builder = DialogPlus.newDialog(this)
				.setContentHolder(holder)//必须设置，ViewHolder或ListHolder或GridHolder
				.setGravity(gravity)//支持三种：BOTTOM (default), TOP or CENTER
				.setExpanded(expanded, 600)//是否可扩展。setExpanded(true)
				.setCancelable(true)

				.setMargin(0, 100, 0, 0)//Add margins to your dialog. They are set to 0 except when gravity is center.
				.setOutMostMargin(0, 0, 0, 0)//Add margins to your outmost view which contains everything. 默认为0

				.setContentWidth(700)
				.setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)

				.setContentBackgroundResource(R.drawable.corner_background)
				.setOverlayBackgroundResource(android.R.color.holo_blue_light)

				.setInAnimation(com.orhanobut.dialogplus.R.anim.fade_in_center)//slide_in_top、slide_in_bottom、fade_in_center
				.setOutAnimation(com.orhanobut.dialogplus.R.anim.fade_out_center)

				.setOnClickListener((dialog, view) -> Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show())
				.setOnItemClickListener((dialog, item, view, position) -> Toast.makeText(this, "onItemClick," + position, 0).show())
				.setOnDismissListener(dialog -> Toast.makeText(this, "onDismiss", Toast.LENGTH_SHORT).show())
				.setOnCancelListener(dialog -> Toast.makeText(this, "onCancel", Toast.LENGTH_SHORT).show())
				.setOnBackPressListener(dialogPlus -> Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show());

		if (adapter != null) builder.setAdapter(adapter);//ListView或GridView时使用的adapter，ViewHolder不需要
		if (new Random().nextBoolean()) builder.setHeader(R.layout.header);
		if (new Random().nextBoolean()) builder.setFooter(R.layout.footer);

		builder.create().show();
	}
}
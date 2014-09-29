package de.mpg.eva.encryptor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private Spinner spinner;
	private EditText msgText;
	private EditText resText;
	private RadioGroup radioFormatGroup;
	private int flag = 1;

	// 1 is Base 64 and 2 is Hexadecimal

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		msgText = (EditText) this.findViewById(R.id.msg);
		msgText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				if (!hasFocus) {
					updateRes();
				}
			}
		});

		resText = (EditText) this.findViewById(R.id.res);

		radioFormatGroup = (RadioGroup) findViewById(R.id.outformat);
		radioFormatGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup parent,
							int checkedId) {
						if (checkedId == R.id.base64)
							flag = 1;
						else
							flag = 2;
						updateRes();
					}

				});

		spinner = (Spinner) this.findViewById(R.id.algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.alg_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// String algo = parent.getItemAtPosition(position).toString();
				updateRes();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public String getMsg() {
		return msgText.getText().toString().trim();
	}

	/*
	 * public void setRes(String res) { resText.setText(res); }
	 */

	public void updateRes() {
		String algo = spinner.getSelectedItem().toString();
		String msg = getMsg();
		MsgDigest md = new MsgDigest(algo, msg);
		resText.setText(md.digest(flag));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

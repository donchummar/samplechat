package com.chatapp.nod.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatapp.nod.R;
import com.chatapp.nod.activity.ActiveUser;
import com.chatapp.nod.activity.ChatRoom;
import com.chatapp.nod.util.Constants;
import com.chatapp.nod.util.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don Chummar on 11/19/2015.
 */
public class UserListFragment extends Fragment {

    private View mFragmentView;
    private Activity mContext;
    private ParseUser userData;
    private ArrayList<ParseUser> uList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.user_list, container, false);
        userData = ActiveUser.INSTANCE.activeUser;
        updateUserStatus(true);
        return mFragmentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadUserList();
    }

    private void loadUserList() {
        final ProgressDialog dia = ProgressDialog.show(mContext, null,
                getString(R.string.alert_wait));
        ParseUser.getQuery().whereNotEqualTo("username", userData.getUsername())
                .findInBackground(new FindCallback<ParseUser>() {

                    @Override
                    public void done(List<ParseUser> li, ParseException e) {
                        dia.dismiss();
                        if (li != null) {
                            if (li.size() == 0)
                                Toast.makeText(mContext,
                                        R.string.msg_no_user_found,
                                        Toast.LENGTH_SHORT).show();

                            uList = new ArrayList<ParseUser>(li);
                            ListView list = (ListView) mFragmentView.findViewById(R.id.list);
                            list.setAdapter(new UserAdapter());
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0,
                                                        View arg1, int pos, long arg3) {
                                    startActivity(new Intent(mContext,
                                            ChatRoom.class).putExtra(
                                            Constants.BUDDY_DETAILS, uList.get(pos)
                                                    .getUsername()));
                                }
                            });
                        } else {
                            Utils.showDialog(mContext, getString(R.string.err_users) + " " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void updateUserStatus(boolean online)
    {
        userData.put("online", online);
        userData.saveEventually();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext  = null;
        updateUserStatus(false);
    }


    private class UserAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return uList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public ParseUser getItem(int arg0)
        {
            return uList.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            if (v == null)
                v = LayoutInflater.from(mContext).inflate(R.layout.chat_item, null);

            ParseUser c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getUsername());
            lbl.setCompoundDrawablesWithIntrinsicBounds(
                    c.getBoolean("online") ? R.drawable.ic_online
                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

            return v;
        }

    }
}


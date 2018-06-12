package kr.hs.dgsw.flow.view.ticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.data.realm.login.LoginHelper;
import kr.hs.dgsw.flow.data.realm.out.OutHelper;
import kr.hs.dgsw.flow.data.realm.out.model.Out;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.view.ticket.adapter.TicketAdapter;
public class TicketActivity extends AppCompatActivity {

    @BindView(R.id.ticket_recycler_view)
    public RecyclerView mRecyclerView;

    private TicketAdapter mTicketAdapter;

    private LoginHelper mLoginHelper;
    private OutHelper mOutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ButterKnife.bind(this);

        mLoginHelper = new LoginHelper(this);
        mOutHelper = new OutHelper(this);

        User loggedInUser = mLoginHelper.getLoggedUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 외출/외박 기록을 현재 로그인 되어있는 회원의 이메일을 이용하여 그 회원의 기록만 가져온다
        List<Out> outList = mOutHelper.getOutsByEmail(loggedInUser.getEmail());
        // 최신순으로 볼 수 있도록 리스트를 뒤집는다
        Collections.reverse(outList);
        mTicketAdapter = new TicketAdapter(this);
        mTicketAdapter.addItems((ArrayList<Out>) outList);
        mRecyclerView.setAdapter(mTicketAdapter);
    }
}

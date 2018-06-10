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
import kr.hs.dgsw.flow.data.realm.loginhistory.LoginHistoryHelper;
import kr.hs.dgsw.flow.data.realm.out.OutHelper;
import kr.hs.dgsw.flow.data.realm.out.model.Out;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.view.ticket.adapter.TicketAdapter;
public class TicketActivity extends AppCompatActivity {

    @BindView(R.id.ticket_recycler_view)
    public RecyclerView mRecyclerView;

    private TicketAdapter mTicketAdapter;

    private LoginHistoryHelper mLoginHistoryHelper;
    private OutHelper mOutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ButterKnife.bind(this);

        mLoginHistoryHelper = new LoginHistoryHelper(this);
        mOutHelper = new OutHelper(this);

        User loggedInUser = mLoginHistoryHelper.getLastLoggedInUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 외출/외박 기록을 현재 로그인 되어있는 회원의 이메일을 이용하여 그 회원의 기록만 가져온다
        List<Out> outList = mOutHelper.getOutsByEmail(loggedInUser.getEmail());
        // 최신순으로 볼 수 있도록 리스트를 뒤집는다
        Collections.reverse(outList);
        mTicketAdapter = makeTicketAdapter(outList);
        mRecyclerView.setAdapter(mTicketAdapter);
    }

    private TicketAdapter makeTicketAdapter(List<Out> outList) {
        ArrayList<HashMap<String, String>> outMaps = new ArrayList<>();

        // Out -> HashMap으로 변환
        for (Out out : outList) {
            outMaps.add(convertToMap(out));
        }

        mRecyclerView.setHasFixedSize(true);
        return new TicketAdapter(this, outMaps);
    }

    /**
     * Out객체를 HashMap으로 변환
     * @param out
     * @return
     */
    private HashMap<String, String> convertToMap(Out out) {
        HashMap<String, String> outMap = new HashMap<>();

        outMap.put(TicketAdapter.KEY_TYPE, out.getOutType() == 0 ? "외출" : "외박");
        outMap.put(TicketAdapter.KEY_STATUS, out.getStatus() == 0 ?
                "승인 대기중.." : out.getStatus() == 1 ? "승인" : "거절");
        outMap.put(TicketAdapter.KEY_OUT_DATE_TIME, out.getOutDateTime());
        outMap.put(TicketAdapter.KEY_IN_DATE_TIME, out.getInDateTime());
        outMap.put(TicketAdapter.KEY_REASON, out.getReason());

        return outMap;
    }
}

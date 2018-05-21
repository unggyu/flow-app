package kr.hs.dgsw.flow.view.ticket.presenter;

import android.support.annotation.NonNull;

import kr.hs.dgsw.flow.data.sqlite.OutHelper;
import kr.hs.dgsw.flow.view.ticket.adapter.TicketAdapter;

public class TicketPresenterImpl implements ITicketContract.Presenter {
    private ITicketContract.View mView;

    private OutHelper mOutHelper;

    private TicketAdapter mTicketAdapter;

    public TicketPresenterImpl(@NonNull ITicketContract.View view) {
        this.mView = view;
    }

    @Override
    public void setView(@NonNull ITicketContract.View view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        this.mView = null;
    }
}

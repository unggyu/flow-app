package kr.hs.dgsw.flow.view.ticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.view.ticket.presenter.ITicketContract;

public class TicketActivity extends AppCompatActivity implements ITicketContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
    }

    @Override
    public void showMessageToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
    }
}

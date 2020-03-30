import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionChannel } from 'app/shared/model/transaction-channel.model';

@Component({
  selector: 'jhi-transaction-channel-detail',
  templateUrl: './transaction-channel-detail.component.html'
})
export class TransactionChannelDetailComponent implements OnInit {
  transactionChannel: ITransactionChannel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionChannel }) => {
      this.transactionChannel = transactionChannel;
    });
  }

  previousState() {
    window.history.back();
  }
}

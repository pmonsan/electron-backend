import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionChannel } from 'app/shared/model/transaction-channel.model';
import { TransactionChannelService } from './transaction-channel.service';

@Component({
  selector: 'jhi-transaction-channel-delete-dialog',
  templateUrl: './transaction-channel-delete-dialog.component.html'
})
export class TransactionChannelDeleteDialogComponent {
  transactionChannel: ITransactionChannel;

  constructor(
    protected transactionChannelService: TransactionChannelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionChannelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionChannelListModification',
        content: 'Deleted an transactionChannel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-channel-delete-popup',
  template: ''
})
export class TransactionChannelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionChannel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionChannelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionChannel = transactionChannel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-channel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-channel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

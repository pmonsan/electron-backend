import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';

@Component({
  selector: 'jhi-transaction-info-delete-dialog',
  templateUrl: './transaction-info-delete-dialog.component.html'
})
export class TransactionInfoDeleteDialogComponent {
  transactionInfo: ITransactionInfo;

  constructor(
    protected transactionInfoService: TransactionInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionInfoListModification',
        content: 'Deleted an transactionInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-info-delete-popup',
  template: ''
})
export class TransactionInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionInfo = transactionInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-info', { outlets: { popup: null } }]);
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

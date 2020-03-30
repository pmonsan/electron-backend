import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionStatus } from 'app/shared/model/transaction-status.model';
import { TransactionStatusService } from './transaction-status.service';

@Component({
  selector: 'jhi-transaction-status-delete-dialog',
  templateUrl: './transaction-status-delete-dialog.component.html'
})
export class TransactionStatusDeleteDialogComponent {
  transactionStatus: ITransactionStatus;

  constructor(
    protected transactionStatusService: TransactionStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionStatusListModification',
        content: 'Deleted an transactionStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-status-delete-popup',
  template: ''
})
export class TransactionStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionStatus = transactionStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-status', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionCommission } from 'app/shared/model/transaction-commission.model';
import { TransactionCommissionService } from './transaction-commission.service';

@Component({
  selector: 'jhi-transaction-commission-delete-dialog',
  templateUrl: './transaction-commission-delete-dialog.component.html'
})
export class TransactionCommissionDeleteDialogComponent {
  transactionCommission: ITransactionCommission;

  constructor(
    protected transactionCommissionService: TransactionCommissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionCommissionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionCommissionListModification',
        content: 'Deleted an transactionCommission'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-commission-delete-popup',
  template: ''
})
export class TransactionCommissionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionCommission }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionCommissionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.transactionCommission = transactionCommission;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-commission', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-commission', { outlets: { popup: null } }]);
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

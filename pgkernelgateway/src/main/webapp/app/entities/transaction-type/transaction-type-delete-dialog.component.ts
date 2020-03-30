import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionType } from 'app/shared/model/transaction-type.model';
import { TransactionTypeService } from './transaction-type.service';

@Component({
  selector: 'jhi-transaction-type-delete-dialog',
  templateUrl: './transaction-type-delete-dialog.component.html'
})
export class TransactionTypeDeleteDialogComponent {
  transactionType: ITransactionType;

  constructor(
    protected transactionTypeService: TransactionTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionTypeListModification',
        content: 'Deleted an transactionType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-type-delete-popup',
  template: ''
})
export class TransactionTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionType = transactionType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-type', { outlets: { popup: null } }]);
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

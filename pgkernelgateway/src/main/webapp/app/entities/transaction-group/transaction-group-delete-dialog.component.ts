import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionGroup } from 'app/shared/model/transaction-group.model';
import { TransactionGroupService } from './transaction-group.service';

@Component({
  selector: 'jhi-transaction-group-delete-dialog',
  templateUrl: './transaction-group-delete-dialog.component.html'
})
export class TransactionGroupDeleteDialogComponent {
  transactionGroup: ITransactionGroup;

  constructor(
    protected transactionGroupService: TransactionGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionGroupListModification',
        content: 'Deleted an transactionGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-group-delete-popup',
  template: ''
})
export class TransactionGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionGroup = transactionGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-group', { outlets: { popup: null } }]);
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

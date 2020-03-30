import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionProperty } from 'app/shared/model/transaction-property.model';
import { TransactionPropertyService } from './transaction-property.service';

@Component({
  selector: 'jhi-transaction-property-delete-dialog',
  templateUrl: './transaction-property-delete-dialog.component.html'
})
export class TransactionPropertyDeleteDialogComponent {
  transactionProperty: ITransactionProperty;

  constructor(
    protected transactionPropertyService: TransactionPropertyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionPropertyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionPropertyListModification',
        content: 'Deleted an transactionProperty'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-property-delete-popup',
  template: ''
})
export class TransactionPropertyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionProperty }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionPropertyDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.transactionProperty = transactionProperty;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-property', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-property', { outlets: { popup: null } }]);
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

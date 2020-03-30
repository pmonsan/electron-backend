import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionPrice } from 'app/shared/model/transaction-price.model';
import { TransactionPriceService } from './transaction-price.service';

@Component({
  selector: 'jhi-transaction-price-delete-dialog',
  templateUrl: './transaction-price-delete-dialog.component.html'
})
export class TransactionPriceDeleteDialogComponent {
  transactionPrice: ITransactionPrice;

  constructor(
    protected transactionPriceService: TransactionPriceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionPriceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionPriceListModification',
        content: 'Deleted an transactionPrice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-price-delete-popup',
  template: ''
})
export class TransactionPriceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionPrice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionPriceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionPrice = transactionPrice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-price', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-price', { outlets: { popup: null } }]);
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

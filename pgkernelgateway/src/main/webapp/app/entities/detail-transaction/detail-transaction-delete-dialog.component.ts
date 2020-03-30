import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetailTransaction } from 'app/shared/model/detail-transaction.model';
import { DetailTransactionService } from './detail-transaction.service';

@Component({
  selector: 'jhi-detail-transaction-delete-dialog',
  templateUrl: './detail-transaction-delete-dialog.component.html'
})
export class DetailTransactionDeleteDialogComponent {
  detailTransaction: IDetailTransaction;

  constructor(
    protected detailTransactionService: DetailTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.detailTransactionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'detailTransactionListModification',
        content: 'Deleted an detailTransaction'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-detail-transaction-delete-popup',
  template: ''
})
export class DetailTransactionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detailTransaction }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DetailTransactionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.detailTransaction = detailTransaction;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/detail-transaction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/detail-transaction', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';
import { PgTransactionType1Service } from './pg-transaction-type-1.service';

@Component({
  selector: 'jhi-pg-transaction-type-1-delete-dialog',
  templateUrl: './pg-transaction-type-1-delete-dialog.component.html'
})
export class PgTransactionType1DeleteDialogComponent {
  pgTransactionType1: IPgTransactionType1;

  constructor(
    protected pgTransactionType1Service: PgTransactionType1Service,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgTransactionType1Service.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgTransactionType1ListModification',
        content: 'Deleted an pgTransactionType1'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-transaction-type-1-delete-popup',
  template: ''
})
export class PgTransactionType1DeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgTransactionType1 }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgTransactionType1DeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgTransactionType1 = pgTransactionType1;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-transaction-type-1', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-transaction-type-1', { outlets: { popup: null } }]);
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

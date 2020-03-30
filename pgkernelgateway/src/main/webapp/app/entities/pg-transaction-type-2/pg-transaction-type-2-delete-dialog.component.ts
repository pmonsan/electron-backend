import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';
import { PgTransactionType2Service } from './pg-transaction-type-2.service';

@Component({
  selector: 'jhi-pg-transaction-type-2-delete-dialog',
  templateUrl: './pg-transaction-type-2-delete-dialog.component.html'
})
export class PgTransactionType2DeleteDialogComponent {
  pgTransactionType2: IPgTransactionType2;

  constructor(
    protected pgTransactionType2Service: PgTransactionType2Service,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgTransactionType2Service.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgTransactionType2ListModification',
        content: 'Deleted an pgTransactionType2'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-transaction-type-2-delete-popup',
  template: ''
})
export class PgTransactionType2DeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgTransactionType2 }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgTransactionType2DeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgTransactionType2 = pgTransactionType2;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-transaction-type-2', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-transaction-type-2', { outlets: { popup: null } }]);
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

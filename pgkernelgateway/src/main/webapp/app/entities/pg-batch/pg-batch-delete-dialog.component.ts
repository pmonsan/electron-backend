import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgBatch } from 'app/shared/model/pg-batch.model';
import { PgBatchService } from './pg-batch.service';

@Component({
  selector: 'jhi-pg-batch-delete-dialog',
  templateUrl: './pg-batch-delete-dialog.component.html'
})
export class PgBatchDeleteDialogComponent {
  pgBatch: IPgBatch;

  constructor(protected pgBatchService: PgBatchService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgBatchService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgBatchListModification',
        content: 'Deleted an pgBatch'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-batch-delete-popup',
  template: ''
})
export class PgBatchDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgBatch }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgBatchDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgBatch = pgBatch;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-batch', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-batch', { outlets: { popup: null } }]);
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

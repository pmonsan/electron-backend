import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';
import { PgMessageModelService } from './pg-message-model.service';

@Component({
  selector: 'jhi-pg-message-model-delete-dialog',
  templateUrl: './pg-message-model-delete-dialog.component.html'
})
export class PgMessageModelDeleteDialogComponent {
  pgMessageModel: IPgMessageModel;

  constructor(
    protected pgMessageModelService: PgMessageModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgMessageModelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgMessageModelListModification',
        content: 'Deleted an pgMessageModel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-message-model-delete-popup',
  template: ''
})
export class PgMessageModelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessageModel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgMessageModelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgMessageModel = pgMessageModel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-message-model', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-message-model', { outlets: { popup: null } }]);
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

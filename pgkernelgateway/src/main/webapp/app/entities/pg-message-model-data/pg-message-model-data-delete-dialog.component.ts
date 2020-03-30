import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgMessageModelData } from 'app/shared/model/pg-message-model-data.model';
import { PgMessageModelDataService } from './pg-message-model-data.service';

@Component({
  selector: 'jhi-pg-message-model-data-delete-dialog',
  templateUrl: './pg-message-model-data-delete-dialog.component.html'
})
export class PgMessageModelDataDeleteDialogComponent {
  pgMessageModelData: IPgMessageModelData;

  constructor(
    protected pgMessageModelDataService: PgMessageModelDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgMessageModelDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgMessageModelDataListModification',
        content: 'Deleted an pgMessageModelData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-message-model-data-delete-popup',
  template: ''
})
export class PgMessageModelDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessageModelData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgMessageModelDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgMessageModelData = pgMessageModelData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-message-model-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-message-model-data', { outlets: { popup: null } }]);
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

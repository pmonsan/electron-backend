import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgData } from 'app/shared/model/pg-data.model';
import { PgDataService } from './pg-data.service';

@Component({
  selector: 'jhi-pg-data-delete-dialog',
  templateUrl: './pg-data-delete-dialog.component.html'
})
export class PgDataDeleteDialogComponent {
  pgData: IPgData;

  constructor(protected pgDataService: PgDataService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgDataListModification',
        content: 'Deleted an pgData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-data-delete-popup',
  template: ''
})
export class PgDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgData = pgData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-data', { outlets: { popup: null } }]);
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

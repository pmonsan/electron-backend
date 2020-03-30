import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgService } from 'app/shared/model/pg-service.model';
import { PgServiceService } from './pg-service.service';

@Component({
  selector: 'jhi-pg-service-delete-dialog',
  templateUrl: './pg-service-delete-dialog.component.html'
})
export class PgServiceDeleteDialogComponent {
  pgService: IPgService;

  constructor(protected pgServiceService: PgServiceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgServiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgServiceListModification',
        content: 'Deleted an pgService'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-service-delete-popup',
  template: ''
})
export class PgServiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgService }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgServiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgService = pgService;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-service', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-service', { outlets: { popup: null } }]);
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

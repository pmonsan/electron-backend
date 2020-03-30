import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgApplicationService } from 'app/shared/model/pg-application-service.model';
import { PgApplicationServiceService } from './pg-application-service.service';

@Component({
  selector: 'jhi-pg-application-service-delete-dialog',
  templateUrl: './pg-application-service-delete-dialog.component.html'
})
export class PgApplicationServiceDeleteDialogComponent {
  pgApplicationService: IPgApplicationService;

  constructor(
    protected pgApplicationServiceService: PgApplicationServiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgApplicationServiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgApplicationServiceListModification',
        content: 'Deleted an pgApplicationService'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-application-service-delete-popup',
  template: ''
})
export class PgApplicationServiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgApplicationService }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgApplicationServiceDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.pgApplicationService = pgApplicationService;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-application-service', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-application-service', { outlets: { popup: null } }]);
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

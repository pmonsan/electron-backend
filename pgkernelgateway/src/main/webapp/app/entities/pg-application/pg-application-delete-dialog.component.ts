import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgApplication } from 'app/shared/model/pg-application.model';
import { PgApplicationService } from './pg-application.service';

@Component({
  selector: 'jhi-pg-application-delete-dialog',
  templateUrl: './pg-application-delete-dialog.component.html'
})
export class PgApplicationDeleteDialogComponent {
  pgApplication: IPgApplication;

  constructor(
    protected pgApplicationService: PgApplicationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgApplicationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgApplicationListModification',
        content: 'Deleted an pgApplication'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-application-delete-popup',
  template: ''
})
export class PgApplicationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgApplication }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgApplicationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgApplication = pgApplication;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-application', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-application', { outlets: { popup: null } }]);
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

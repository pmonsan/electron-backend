import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgMessageStatus } from 'app/shared/model/pg-message-status.model';
import { PgMessageStatusService } from './pg-message-status.service';

@Component({
  selector: 'jhi-pg-message-status-delete-dialog',
  templateUrl: './pg-message-status-delete-dialog.component.html'
})
export class PgMessageStatusDeleteDialogComponent {
  pgMessageStatus: IPgMessageStatus;

  constructor(
    protected pgMessageStatusService: PgMessageStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgMessageStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgMessageStatusListModification',
        content: 'Deleted an pgMessageStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-message-status-delete-popup',
  template: ''
})
export class PgMessageStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessageStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgMessageStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgMessageStatus = pgMessageStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-message-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-message-status', { outlets: { popup: null } }]);
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

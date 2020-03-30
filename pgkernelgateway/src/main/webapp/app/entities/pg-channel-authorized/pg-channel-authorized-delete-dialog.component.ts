import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';
import { PgChannelAuthorizedService } from './pg-channel-authorized.service';

@Component({
  selector: 'jhi-pg-channel-authorized-delete-dialog',
  templateUrl: './pg-channel-authorized-delete-dialog.component.html'
})
export class PgChannelAuthorizedDeleteDialogComponent {
  pgChannelAuthorized: IPgChannelAuthorized;

  constructor(
    protected pgChannelAuthorizedService: PgChannelAuthorizedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgChannelAuthorizedService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgChannelAuthorizedListModification',
        content: 'Deleted an pgChannelAuthorized'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-channel-authorized-delete-popup',
  template: ''
})
export class PgChannelAuthorizedDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgChannelAuthorized }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgChannelAuthorizedDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.pgChannelAuthorized = pgChannelAuthorized;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-channel-authorized', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-channel-authorized', { outlets: { popup: null } }]);
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

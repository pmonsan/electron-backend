import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgDetailMessage } from 'app/shared/model/pg-detail-message.model';
import { PgDetailMessageService } from './pg-detail-message.service';

@Component({
  selector: 'jhi-pg-detail-message-delete-dialog',
  templateUrl: './pg-detail-message-delete-dialog.component.html'
})
export class PgDetailMessageDeleteDialogComponent {
  pgDetailMessage: IPgDetailMessage;

  constructor(
    protected pgDetailMessageService: PgDetailMessageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgDetailMessageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgDetailMessageListModification',
        content: 'Deleted an pgDetailMessage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-detail-message-delete-popup',
  template: ''
})
export class PgDetailMessageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgDetailMessage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgDetailMessageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgDetailMessage = pgDetailMessage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-detail-message', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-detail-message', { outlets: { popup: null } }]);
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

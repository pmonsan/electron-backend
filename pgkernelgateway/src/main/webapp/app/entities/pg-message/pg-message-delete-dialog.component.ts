import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgMessage } from 'app/shared/model/pg-message.model';
import { PgMessageService } from './pg-message.service';

@Component({
  selector: 'jhi-pg-message-delete-dialog',
  templateUrl: './pg-message-delete-dialog.component.html'
})
export class PgMessageDeleteDialogComponent {
  pgMessage: IPgMessage;

  constructor(protected pgMessageService: PgMessageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgMessageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgMessageListModification',
        content: 'Deleted an pgMessage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-message-delete-popup',
  template: ''
})
export class PgMessageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgMessage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgMessageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgMessage = pgMessage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-message', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-message', { outlets: { popup: null } }]);
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

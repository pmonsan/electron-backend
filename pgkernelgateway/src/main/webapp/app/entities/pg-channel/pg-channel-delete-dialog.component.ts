import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgChannel } from 'app/shared/model/pg-channel.model';
import { PgChannelService } from './pg-channel.service';

@Component({
  selector: 'jhi-pg-channel-delete-dialog',
  templateUrl: './pg-channel-delete-dialog.component.html'
})
export class PgChannelDeleteDialogComponent {
  pgChannel: IPgChannel;

  constructor(protected pgChannelService: PgChannelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgChannelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgChannelListModification',
        content: 'Deleted an pgChannel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-channel-delete-popup',
  template: ''
})
export class PgChannelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgChannel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgChannelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgChannel = pgChannel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-channel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-channel', { outlets: { popup: null } }]);
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

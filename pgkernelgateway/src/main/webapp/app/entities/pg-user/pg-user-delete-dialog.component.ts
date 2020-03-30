import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgUser } from 'app/shared/model/pg-user.model';
import { PgUserService } from './pg-user.service';

@Component({
  selector: 'jhi-pg-user-delete-dialog',
  templateUrl: './pg-user-delete-dialog.component.html'
})
export class PgUserDeleteDialogComponent {
  pgUser: IPgUser;

  constructor(protected pgUserService: PgUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pgUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pgUserListModification',
        content: 'Deleted an pgUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pg-user-delete-popup',
  template: ''
})
export class PgUserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pgUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PgUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pgUser = pgUser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pg-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pg-user', { outlets: { popup: null } }]);
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

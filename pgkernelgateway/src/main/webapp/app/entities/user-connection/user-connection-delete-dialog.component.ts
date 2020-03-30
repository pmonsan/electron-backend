import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserConnection } from 'app/shared/model/user-connection.model';
import { UserConnectionService } from './user-connection.service';

@Component({
  selector: 'jhi-user-connection-delete-dialog',
  templateUrl: './user-connection-delete-dialog.component.html'
})
export class UserConnectionDeleteDialogComponent {
  userConnection: IUserConnection;

  constructor(
    protected userConnectionService: UserConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userConnectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userConnectionListModification',
        content: 'Deleted an userConnection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-connection-delete-popup',
  template: ''
})
export class UserConnectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userConnection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserConnectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userConnection = userConnection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-connection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-connection', { outlets: { popup: null } }]);
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

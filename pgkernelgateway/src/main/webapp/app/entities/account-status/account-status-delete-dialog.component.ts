import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountStatus } from 'app/shared/model/account-status.model';
import { AccountStatusService } from './account-status.service';

@Component({
  selector: 'jhi-account-status-delete-dialog',
  templateUrl: './account-status-delete-dialog.component.html'
})
export class AccountStatusDeleteDialogComponent {
  accountStatus: IAccountStatus;

  constructor(
    protected accountStatusService: AccountStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountStatusListModification',
        content: 'Deleted an accountStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-status-delete-popup',
  template: ''
})
export class AccountStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountStatus = accountStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/account-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/account-status', { outlets: { popup: null } }]);
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

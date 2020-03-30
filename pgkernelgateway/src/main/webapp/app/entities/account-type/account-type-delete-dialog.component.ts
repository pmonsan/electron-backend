import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountType } from 'app/shared/model/account-type.model';
import { AccountTypeService } from './account-type.service';

@Component({
  selector: 'jhi-account-type-delete-dialog',
  templateUrl: './account-type-delete-dialog.component.html'
})
export class AccountTypeDeleteDialogComponent {
  accountType: IAccountType;

  constructor(
    protected accountTypeService: AccountTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountTypeListModification',
        content: 'Deleted an accountType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-type-delete-popup',
  template: ''
})
export class AccountTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountType = accountType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/account-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/account-type', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountFeature } from 'app/shared/model/account-feature.model';
import { AccountFeatureService } from './account-feature.service';

@Component({
  selector: 'jhi-account-feature-delete-dialog',
  templateUrl: './account-feature-delete-dialog.component.html'
})
export class AccountFeatureDeleteDialogComponent {
  accountFeature: IAccountFeature;

  constructor(
    protected accountFeatureService: AccountFeatureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountFeatureService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountFeatureListModification',
        content: 'Deleted an accountFeature'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-account-feature-delete-popup',
  template: ''
})
export class AccountFeatureDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountFeature }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountFeatureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accountFeature = accountFeature;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/account-feature', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/account-feature', { outlets: { popup: null } }]);
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

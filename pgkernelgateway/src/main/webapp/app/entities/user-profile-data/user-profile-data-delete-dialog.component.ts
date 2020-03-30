import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserProfileData } from 'app/shared/model/user-profile-data.model';
import { UserProfileDataService } from './user-profile-data.service';

@Component({
  selector: 'jhi-user-profile-data-delete-dialog',
  templateUrl: './user-profile-data-delete-dialog.component.html'
})
export class UserProfileDataDeleteDialogComponent {
  userProfileData: IUserProfileData;

  constructor(
    protected userProfileDataService: UserProfileDataService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userProfileDataService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userProfileDataListModification',
        content: 'Deleted an userProfileData'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-user-profile-data-delete-popup',
  template: ''
})
export class UserProfileDataDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userProfileData }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserProfileDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userProfileData = userProfileData;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/user-profile-data', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/user-profile-data', { outlets: { popup: null } }]);
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

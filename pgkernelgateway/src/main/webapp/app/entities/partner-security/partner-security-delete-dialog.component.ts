import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartnerSecurity } from 'app/shared/model/partner-security.model';
import { PartnerSecurityService } from './partner-security.service';

@Component({
  selector: 'jhi-partner-security-delete-dialog',
  templateUrl: './partner-security-delete-dialog.component.html'
})
export class PartnerSecurityDeleteDialogComponent {
  partnerSecurity: IPartnerSecurity;

  constructor(
    protected partnerSecurityService: PartnerSecurityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.partnerSecurityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'partnerSecurityListModification',
        content: 'Deleted an partnerSecurity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-partner-security-delete-popup',
  template: ''
})
export class PartnerSecurityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partnerSecurity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PartnerSecurityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.partnerSecurity = partnerSecurity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/partner-security', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/partner-security', { outlets: { popup: null } }]);
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

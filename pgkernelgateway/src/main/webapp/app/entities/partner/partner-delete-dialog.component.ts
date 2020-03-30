import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';

@Component({
  selector: 'jhi-partner-delete-dialog',
  templateUrl: './partner-delete-dialog.component.html'
})
export class PartnerDeleteDialogComponent {
  partner: IPartner;

  constructor(protected partnerService: PartnerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.partnerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'partnerListModification',
        content: 'Deleted an partner'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-partner-delete-popup',
  template: ''
})
export class PartnerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partner }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PartnerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.partner = partner;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/partner', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/partner', { outlets: { popup: null } }]);
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

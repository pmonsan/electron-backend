import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartnerType } from 'app/shared/model/partner-type.model';
import { PartnerTypeService } from './partner-type.service';

@Component({
  selector: 'jhi-partner-type-delete-dialog',
  templateUrl: './partner-type-delete-dialog.component.html'
})
export class PartnerTypeDeleteDialogComponent {
  partnerType: IPartnerType;

  constructor(
    protected partnerTypeService: PartnerTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.partnerTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'partnerTypeListModification',
        content: 'Deleted an partnerType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-partner-type-delete-popup',
  template: ''
})
export class PartnerTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ partnerType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PartnerTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.partnerType = partnerType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/partner-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/partner-type', { outlets: { popup: null } }]);
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

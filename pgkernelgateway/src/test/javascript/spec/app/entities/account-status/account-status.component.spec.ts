/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountStatusComponent } from 'app/entities/account-status/account-status.component';
import { AccountStatusService } from 'app/entities/account-status/account-status.service';
import { AccountStatus } from 'app/shared/model/account-status.model';

describe('Component Tests', () => {
  describe('AccountStatus Management Component', () => {
    let comp: AccountStatusComponent;
    let fixture: ComponentFixture<AccountStatusComponent>;
    let service: AccountStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountStatusComponent],
        providers: []
      })
        .overrideTemplate(AccountStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

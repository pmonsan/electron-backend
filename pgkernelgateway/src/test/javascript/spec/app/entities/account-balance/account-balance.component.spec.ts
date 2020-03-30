/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountBalanceComponent } from 'app/entities/account-balance/account-balance.component';
import { AccountBalanceService } from 'app/entities/account-balance/account-balance.service';
import { AccountBalance } from 'app/shared/model/account-balance.model';

describe('Component Tests', () => {
  describe('AccountBalance Management Component', () => {
    let comp: AccountBalanceComponent;
    let fixture: ComponentFixture<AccountBalanceComponent>;
    let service: AccountBalanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountBalanceComponent],
        providers: []
      })
        .overrideTemplate(AccountBalanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountBalanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountBalanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountBalance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountBalances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

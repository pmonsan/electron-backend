/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { DetailContractComponent } from 'app/entities/detail-contract/detail-contract.component';
import { DetailContractService } from 'app/entities/detail-contract/detail-contract.service';
import { DetailContract } from 'app/shared/model/detail-contract.model';

describe('Component Tests', () => {
  describe('DetailContract Management Component', () => {
    let comp: DetailContractComponent;
    let fixture: ComponentFixture<DetailContractComponent>;
    let service: DetailContractService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [DetailContractComponent],
        providers: []
      })
        .overrideTemplate(DetailContractComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetailContractComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailContractService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DetailContract(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.detailContracts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

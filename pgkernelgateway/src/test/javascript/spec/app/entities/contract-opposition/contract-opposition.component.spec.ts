/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ContractOppositionComponent } from 'app/entities/contract-opposition/contract-opposition.component';
import { ContractOppositionService } from 'app/entities/contract-opposition/contract-opposition.service';
import { ContractOpposition } from 'app/shared/model/contract-opposition.model';

describe('Component Tests', () => {
  describe('ContractOpposition Management Component', () => {
    let comp: ContractOppositionComponent;
    let fixture: ComponentFixture<ContractOppositionComponent>;
    let service: ContractOppositionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ContractOppositionComponent],
        providers: []
      })
        .overrideTemplate(ContractOppositionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractOppositionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractOppositionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContractOpposition(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contractOppositions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
